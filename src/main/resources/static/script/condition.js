//let button = document.getElementById("updateButton");
//button.disabled  = true
function deactivateButton() {
  buttons = document.getElementsByName('updateButton');
  for(var i=0, n=buttons.length;i<n;i++) {
    checkboxes[i].disabled = true;
  }
}
deactivateButton()
