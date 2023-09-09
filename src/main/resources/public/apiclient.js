apiclient = (function() {

    let _publicFunctions = {};

    let _apiURL = "http://localhost:35000";

    _publicFunctions.getMovieByTittle = function (tittle, callback) {
        return $.get(`${_apiURL}/?${tittle}`, (data) => {
                    callback(data);
                }).fail();
    }


    _publicFunctions.post = function (name, lastName, callback) {
        return $.ajax({
                url: `/hello`,
                type: 'POST',
                data: JSON.stringify({
                    name: name,
                    lastName: lastName,
                }),
                contentType: "application/json",
                success: data => callback(data),
            });
    };

    _publicFunctions.get = function (name, callback) {
        return $.get(`/hello?name=${name}`, (data) => {
                    callback(data);
                }).fail();
    }

    return _publicFunctions;
})()