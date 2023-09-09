app = (function(api){
    let _publicFunctions = {};

    let _renderSearch = function(data) {
        $(document).ready(() => {
            let image = `<img src=${data.Poster}/>`;
            $("#image-movie").html(image);
            $("#title").text(`Titulo: ${data.Title}`);
            $("#year").text(`Año: ${data.Year}`);
            $("#genre").text(`Género: ${data.Genre}`);
            $("#plot").text(`Sipnosis: ${data.Plot}`);
        });
    }

    let _update = function(data) {
        $(document).ready(() => {
            $("#getrespmsg").html(data);
        });
    }

    let _updatePost = function(data) {
        $(document).ready(() => {
            $("#postrespmsg").html(data);
        });
    }

    _publicFunctions.searchMovieByTittle = function(tittle) {
        api.getMovieByTittle(tittle, _renderSearch);
    }

    _publicFunctions.post = function(name, lastName) {
        api.post(name, lastName, _updatePost);
    }

    _publicFunctions.get = function(name) {
        api.get(name, _update);
    }

    return _publicFunctions;
})(apiclient);