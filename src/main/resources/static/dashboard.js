// Production API URL
const API_URL = "https://alzaaspr-production.up.railway.app";

// Exercises grouped by body part (matching backend)
const exercisesByBodyPart = {
  Chest: ["Bench Press"],
  Back: ["Barbell Row", "Pull-up", "Deadlift"],
  Shoulders: ["Overhead Press"],
  Arms: ["Dumbbell Curl"],
  LegsQuads: ["Squat", "Leg Press"],
  LegsHamstrings: ["Deadlift"],
  Core: []
};

const bodyPartSelect = document.getElementById("bodyPart");
const exerciseSelect = document.getElementById("exercise");
const setsInput = document.getElementById("sets");
const setsContainer = document.getElementById("setsContainer");
const logBtn = document.getElementById("logBtn");
const logContainer = document.getElementById("logContainer");

// Map of exercise name → exerciseId (from backend)
let exercisesMap = {};

// Load exercises from backend and build mapping
async function loadExercises() {
  const token = localStorage.getItem("authToken");
  
  if (!token) {
    alert("No token found. Please log in again.");
    return;
  }

  try {
    const res = await fetch(`${API_URL}/api/exercises`, {
      headers: { "Authorization": token }
    });

    if (!res.ok) {
      throw new Error("Failed to fetch exercises");
    }

    const exercises = await res.json();
    exercisesMap = {};
    exercises.forEach(ex => {
      exercisesMap[ex.name] = ex.id;
    });
  } catch (err) {
    console.error("Error loading exercises:", err);
    alert("Could not load exercises from server.");
  }
}

// Populate exercises when body part changes
bodyPartSelect.addEventListener("change", () => {
  const bodyPart = bodyPartSelect.value;
  exerciseSelect.innerHTML = '<option value="">-- Select Exercise --</option>';
  if (bodyPart && exercisesByBodyPart[bodyPart]) {
    exercisesByBodyPart[bodyPart].forEach(ex => {
      const option = document.createElement("option");
      option.value = ex;
      option.textContent = ex;
      exerciseSelect.appendChild(option);
    });
    exerciseSelect.disabled = false;
  } else {
    exerciseSelect.disabled = true;
  }
});

// Generate weight/reps inputs based on number of sets
setsInput.addEventListener("input", () => {
  const numberOfSets = parseInt(setsInput.value) || 0;
  setsContainer.innerHTML = "";
  for (let i = 1; i <= numberOfSets; i++) {
    const div = document.createElement("div");
    div.classList.add("set-inputs");
    div.innerHTML = `
      <input type="number" placeholder="Peso del set ${i}">
      <input type="number" placeholder="Reps del set ${i}">
    `;
    setsContainer.appendChild(div);
  }
});

// Log workout and send to backend
logBtn.addEventListener("click", async () => {
  const bodyPart = bodyPartSelect.value;
  const exerciseName = exerciseSelect.value;
  const sets = setsContainer.querySelectorAll(".set-inputs");

  if (!bodyPart || !exerciseName || sets.length === 0) {
    alert("Por favor completa todos los campos antes de registrar.");
    return;
  }

  // Get the actual exerciseId from backend map
  const exerciseId = exercisesMap[exerciseName];
  if (!exerciseId) {
    alert("Exercise not found in backend. Please reload the page.");
    return;
  }

  // Prepare workout sets
  let logSets = [];
  sets.forEach((setDiv) => {
    const weight = parseFloat(setDiv.children[0].value) || 0;
    const reps = parseInt(setDiv.children[1].value) || 0;
    logSets.push({ reps, weight });
  });

  const token = localStorage.getItem("authToken");
  if (!token) {
    alert("No token found. Please log in again.");
    return;
  }

  const log = {
    exerciseId: exerciseId,
    date: new Date().toISOString().split("T")[0], // YYYY-MM-DD
    sets: logSets
  };

  try {
    const response = await fetch(`${API_URL}/api/logs`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": token
      },
      body: JSON.stringify(log)
    });

    const text = await response.text();
    console.log("Raw response:", text);
    console.log("Response length:", text.length);

    let result;
    try {
      result = JSON.parse(text);
    } catch (e) {
      console.error("Failed to parse JSON:", e);
      console.log("First 500 chars:", text.substring(0, 500));
      alert("Server returned invalid JSON");
      return;
    }

    if (result.success) {
      let logText = `<strong>${bodyPart} - ${exerciseName}</strong><br>`;
      logSets.forEach((entry, i) => {
        logText += `Set ${i + 1}: ${entry.weight} lbs x ${entry.reps} reps<br>`;
      });

      const logEntry = document.createElement("div");
      logEntry.classList.add("log-entry");
      logEntry.innerHTML = logText;
      logContainer.appendChild(logEntry);

      setsInput.value = "";
      setsContainer.innerHTML = "";
    } else {
      alert("Error saving workout log: " + result.message);
    }
  } catch (error) {
    console.error("Error:", error);
    alert("Could not connect to server.");
  }
});

// Load exercises immediately when dashboard opens
loadExercises();