import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

const endpoint = {
    users1: '/msg',

};

function postData(data, callback){
    let request = new Request(HOST.backend_api +endpoint.users1, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });
    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}



export {
    postData,
};
