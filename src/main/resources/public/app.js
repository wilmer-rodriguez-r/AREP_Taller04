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

    _publicFunctions.searchMovieByTittle = function(tittle) {
        api.getMovieByTittle(tittle, _renderSearch);
    }

    return _publicFunctions;
})(apiclient);