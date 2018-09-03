/**
 * Initializes panel render
 */
function loadPanel() {
    fetch('http://localhost:8080/phile/files', {
      method: 'POST',
      headers: {
        "Authorization": localStorage.getItem('asciiphile')
      }})
      .then(res => res.json())
      .then(grabUserFiles);
}

/**
 * Logically groups all user file information
 */
function grabUserFiles(userFiles) {
    const result = userFiles.reduce((acc, {
      directory,
      name
    }) => {
      acc[directory] ? acc[directory].push(name) : (acc[directory] = [name]);
      return acc;
    }, {});
    generateFileTree(result);
}

/**
 * Generates the associated file tree based on the grouped data
 */
function generateFileTree(result) {
    for (var key in result) {
        $("#dirTree").append("<li>"+ key +"</li>");
        result[key].forEach(function(data) {
            $("#dirTree").append("<ul><li>"+data+"</li></ul>");
        });
    }
    //$("#dirTree").append("<li>Parent Directory</li><ul><li>child1</li></ul><ul><li>child2</li></ul>");
}



