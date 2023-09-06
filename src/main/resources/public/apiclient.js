apiclient = (function() {

    let _apiURL = "http://localhost:35000";

    return {
        getMovieByTittle:function(tittle, callback){
            $.get(`${_apiURL}/?${tittle}`, (data) => {
                callback(data);
            }).fail();
		},
    }
})()