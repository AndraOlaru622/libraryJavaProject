Books Table

localhost:8080/books/all

localhost:8080/books/getByTitle?title=book3

localhost:8080/books/getById?id=4

localhost:8080/books/add
{
        "idAuthor": 1,
        "title": "book10",
        "nrCopies": 20
}

localhost:8080/books/getAuthorName?title=book10

localhost:8080/books/delete?title=book8


Borrowings Table

localhost:8080/borrowings/all

//Add then delete
localhost:8080/borrowings/add?user=user1&book=book10
localhost:8080/books/getByTitle?title=book10
localhost:8080/borrowings/delete?book=book10&user=user1
localhost:8080/books/getByTitle?title=book10

//Borrowings by user
localhost:8080/borrowings/getByUser?user=user1

//Borrowings for books
localhost:8080/borrowings/getByBook?book=book3

Authors Table
localhost:8080/authors/all

localhost:8080/authors/add
{
    "name": "author5"
}
localhost:8080/authors/getByName?name=author7
localhost:8080/authors/delete?author=author7

Users Table

localhost:8080/users/all
localhost:8080/users/add
{
    "name": "newUser2",
    "email": "user2@gmail.com",
    "address": "add2"
}
