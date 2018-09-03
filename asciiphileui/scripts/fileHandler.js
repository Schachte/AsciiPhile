(function () {
	var fileCatcher = document.getElementById('file-catcher');
  var fileInput = document.getElementById('file-input');
  var fileListDisplay = document.getElementById('file-list-display');

  var fileList = [];
  var renderFileList, sendFile;

  fileCatcher.addEventListener('submit', function (evnt) {
      console.log("This is getting called");
  	evnt.preventDefault();
    fileList.forEach(function (file) {
    	sendFile(file);
    });
  });

  fileInput.addEventListener('change', function (evnt) {
 		fileList = [];
  	for (var i = 0; i < fileInput.files.length; i++) {
    	fileList.push(fileInput.files[i]);
    }
    renderFileList();
  });

  renderFileList = function () {
  	fileListDisplay.innerHTML = '';
    fileList.forEach(function (file, index) {
    	var fileDisplayEl = document.createElement('p');
      fileDisplayEl.innerHTML = (index + 1) + ': ' + file.name;
      fileListDisplay.appendChild(fileDisplayEl);
    });
  };

  sendFile = function (file) {
  	var formData = new FormData();
    var request = new XMLHttpRequest();
      debugger;

    formData.set('file', file);
    request.open("POST", 'http://localhost:8080/phile/upload');
    request.setRequestHeader("Authorization", localStorage.getItem('asciiphile'));
    request.send(formData);
  };
})();
