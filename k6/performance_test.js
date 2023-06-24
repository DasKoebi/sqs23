import http from 'k6/http'
import { check, sleep, group } from 'k6'

export let options = {
    stages: [
      { duration: '1m', target: 50 }, 
      { duration: '2m', target: 50 }, 
      { duration: '1m', target: 0 }   
    ],
    thresholds: {
      http_req_duration: ['p(95)<200'],
      checks: ['rate>0.9'],
      'group_duration{group:::individualRequests}': ['avg < 100'],
      'group_duration{group:::batchRequests}': ['avg < 50'],
      http_req_failed: ['rate<0.01']
    }
  };
    export default function () {
        group('Get Book Details', function () {
          let response = http.get("http://localhost:8080/v1/books/1569319014");
      
          check(response, {
            'Response is 200 OK': (r) => r.status === 200,
            'Correct response body': (r) => r.body === 'One Piece, Vol. 1'
          });
        });
      
        sleep(0.3);
}
