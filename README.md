POST /api/quizzes endpoint is used for adding a new quiz. The requests must contain a JSON as the request's body with the four fields:
title: a string, required;
text: a string, required;
options: an array of strings, required, should contain at least 2 items;
answer: an array of integer indexes of correct options, can be absent or empty if all options are wrong.
structure of the request body:
{
  "title": "<string, not null, not empty>",
  "text": "<string, <not null, not empty>",
  "options": ["<string 1>","<string 2>","<string 3>", ...],
  "answer": [<integer>,<integer>, ...]
}

For example, if answer equals to [0,2] it means that the first and the third item from the options array ("<string 1>" and "<string 3>") are correct.

The server response is a JSON with four fields: id, title, text and options. Here is an example:
{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>","<string 2>","<string 3>", ...]
}


The id field is a generated unique integer identifier for the quiz. Also, the response may or may not include the answer field depending on your wishes. 
This is not very important for this operation.
If the request JSON does not contain title or text, or they are empty strings (""), then the server responds with the 400 (Bad request) status code. 
If the number of options in the quiz is less than 2, the server returns the same status code.

To solve a quiz, the client sends the a JSON that contains the single key "answer" which value is and array of indexes of all chosen options as the answer:
{
  "answer": [<integer>, <integer>, ...]
}
This is POST requests to the /api/quizzes/{id}/solve endpoint

The service returns a JSON with two fields: success (true or false) and feedback (just a string). There are three possible responses.
If the passed answer is correct:
{
  "success":true,
  "feedback":"Congratulations, you're right!"
}
If the answer is incorrect:
{
  "success":false,
  "feedback":"Wrong answer! Please, try again."
}
If the specified quiz does not exist, the server returns the 404 NOT FOUND status code.

GET /api/quizzes/{id} endpoint is used to get a quiz by id. The server must response with a JSON in the following format:

{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>","<string 2>","<string 3>", ...]
}

GET /api/quizzes?page={number} endpoint gets all existing quizzes in the service. The response contains a JSON array of quizzes like the following:

{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>","<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>", "<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>", ...]}
  ]
}



To register a new user, the client needs to send a JSON to POST /api/register endpoint in the following format:
{
  "email": "<username>@<domain>.<extension>",
  "password": "<string, at least 5 characters long>"
}

The service returns 200 (OK) status code if the registration has been completed successfully.
If the email is already taken by another user, the service will return the 400 (BAD REQUEST) status code.
Here are some additional restrictions to the format of user credentials:
the email must have a valid format (with @ and .);
the password must have at least five characters.
If any of them is not satisfied, the service returns the 400 (BAD REQUEST) status code.
All the following operations need a registered user to be successfully completed.
DELETE request to /api/quizzes/{id} allows a user to delete their quiz.
If the operation was successful, the service returns the 204 (NO CONTENT) status code without any content.
If the specified quiz does not exist, the server returns 404 (NOT FOUND). If the specified user is not the author of this quiz, 
the response is the 403 (FORBIDDEN) status code.

API returns only 10 quizzes at once and supports the ability to specify which portion of quizzes is needed (paging).
API returns information about successful completions of quizzes by the authenticated user. This endpoint also supports paging.

GET /api/quizzes/completed?page={number} endpoint will accept the page parameter together with the user auth data 
to provide the specified part of all completions of quizzes for the authenticated user. Each completion is represented by a JSON object in the following format:
{
  "id": <quiz id>,
  "completedAt": <DATE_TIME in ISO Date Time Format (yyyy-MM-dd'T'HH:mm:ss.SSSXXX)>
}

A response is divided into pages since the service may return a lot of data. It contains a JSON with quiz completions 
(inside content) and some additional metadata as in the previous operation.
Response example:
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"}
  ]
}

Since it is allowed to solve a quiz multiple times, the response may contain duplicate quizzes, but with the different completion date. 
The completions must be sorted by their completion time in descending order, i.e. newer completions first, older completions last.

If there are no completed quizzes for the authenticated user, content is empty []. 
If the user is authenticated, the status code is 200 (OK); otherwise, it's 401 (UNAUTHORIZED).
