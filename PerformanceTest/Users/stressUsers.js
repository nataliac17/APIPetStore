import { sleep, check } from "k6";
import http from "k6/http"

export const options = {
    
    stages:[
        {
            duration: "10s",
            target: 500,
        },
        {
            duration: "50s",
            target: 300,
        },
        {
            duration: "30s",
            target: 700,
        },
        {
            duration: "15s",
            target: 100,
        }
    ]
    
}

const BASE_URL ="http://localhost:10000/api/v3";
let username = 'user1';
let password = 'XXXXXXXXXXX'; 
export default function() {
    let headersList = {headers:{
        "Accept": "application/json",
        "Content-Type": "application/json"
        }
    }
    let urlLoginUser= BASE_URL + `/user/login?username=${username}&password=${password}`;

    let requestLogInUser = http.get(urlLoginUser);
    let checkLogIn = check(requestLogInUser, {
        "successful login": (r) => r.status === 200
    });

    sleep(1);

    if(checkLogIn){
        let urlLogOut = BASE_URL + `/user/logout`;
        let requestLogOut = http.get(urlLogOut);

        check(requestLogOut, {
            "successful Log Out": (r) => r.status === 200
        });
    }

}
