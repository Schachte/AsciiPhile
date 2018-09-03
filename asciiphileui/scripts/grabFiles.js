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
}

/**
 * Generates the associated file tree based on the grouped data
 */
function generateFileTree() {

}



