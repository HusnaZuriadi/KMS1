// Sidebar toggle
function toggleSidebar() {
  const sidebar = document.getElementById("sidebar");
  const dashboard = document.getElementById("dashboard");

  sidebar.classList.toggle("show");

  if (dashboard) {
    dashboard.classList.toggle("shifted");
  }
}

// Show the form
function toggleForm() {
  const form = document.getElementById("childForm");
  const formSection = document.getElementById("formSection");

  if (form) form.reset();
  if (formSection) formSection.style.display = "block";
}

// Hide the form
function hideForm() {
  const formSection = document.getElementById("formSection");
  if (formSection) formSection.style.display = "none";
}

// ✅ Make this function global so DOB onchange can trigger it
function calculateAge() {
  const dobInput = document.getElementById("dob");
  const ageInput = document.getElementById("age");

  const dob = new Date(dobInput.value);
  const today = new Date();

  if (dobInput.value) {
    let age = today.getFullYear() - dob.getFullYear();
    const m = today.getMonth() - dob.getMonth();

    if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
      age--;
    }

    ageInput.value = age >= 0 ? age : '';
  } else {
    ageInput.value = '';
  }
}

// Optional: Alert on form submission
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("childForm");

  if (form) {
    form.addEventListener("submit", function (e) {
      const name = document.querySelector('input[name="name"]').value.trim();
      const photoFile = document.querySelector('input[name="photo"]').files[0];

      if (!name) {
        alert("Please enter the student's name.");
        e.preventDefault();
        return;
      }

      if (!photoFile) {
        const confirmNoPhoto = confirm("No photo uploaded. Continue?");
        if (!confirmNoPhoto) {
          e.preventDefault();
        }
      }
    });
  }
});
