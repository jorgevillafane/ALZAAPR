// history.js

async function loadHistory() {
  const token = localStorage.getItem("authToken");
  if (!token) {
    document.getElementById("historyContainer").innerText = "Not logged in.";
    return;
  }

  try {
    const res = await fetch("http://localhost:8080/api/workout-logs/history", {
      headers: {
        "Authorization": token
      }
    });

    if (!res.ok) {
      document.getElementById("historyContainer").innerText = "Error loading history.";
      return;
    }

    const logs = await res.json();

    // Group logs by date
    const grouped = {};
    logs.forEach(log => {
      const date = log.date;
      if (!grouped[date]) grouped[date] = [];
      grouped[date].push(log);
    });

    const container = document.getElementById("historyContainer");
    container.innerHTML = "";

    for (const date in grouped) {
      const section = document.createElement("div");
      section.classList.add("history-day");
      section.innerHTML = `<h3>${date}</h3>`;
      const ul = document.createElement("ul");

      grouped[date].forEach(log => {
        const li = document.createElement("li");

        let setsText = "";
        if (log.sets && log.sets.length > 0) {
          setsText = log.sets
            .map((s, i) => `Set ${i + 1}: ${s.weight} lbs x ${s.reps} reps`)
            .join(" | ");
        } else {
          setsText = "No sets recorded";
        }

        li.innerHTML = `<strong>${log.exercise.name}</strong> — ${setsText}`;
        ul.appendChild(li);
      });

      section.appendChild(ul);
      container.appendChild(section);
    }
  } catch (err) {
    console.error("Error loading history:", err);
    document.getElementById("historyContainer").innerText = "Server error.";
  }
}

document.addEventListener("DOMContentLoaded", loadHistory);
