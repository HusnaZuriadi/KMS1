/**
 * 
 */

// Show/hide Teacher Type dropdown
function showTeacherType() {
     const role = document.getElementById("roleSelect").value;
     const teacherTypeDiv = document.getElementById("teacherType");
     const assignAdminDiv = document.getElementById("assignAdmin");

     if (role === "Teacher") {
       teacherTypeDiv.classList.remove("hidden");
       assignAdminDiv.classList.remove("hidden");
     } else {
       teacherTypeDiv.classList.add("hidden");
       assignAdminDiv.classList.add("hidden");
     }
   }

// Validate password match before submitting
function validateForm() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (password !== confirmPassword) {
    alert("Passwords do not match!");
    return false;
  }
  return true;
}

