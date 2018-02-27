"""
book.py

Contains all API endpoints for interfacing with a book.

Created by Stephen Andrews, Februrary 17, 2018.
"""

from flask import Blueprint, jsonify
import requests

from book_smart.models import Author, Book
from book_smart.extensions import db


book_view = Blueprint('book', __name__)

@book_view.route('/', methods=['GET'])
@book_view.route('/<isbn>', methods=['GET'])
def get(isbn=None):
    """Get a book's information by its ISBN-10 identifier."""

    if isbn:
        return jsonify(get_book_data(isbn))

    return jsonify([b.to_json() for b in Book.query.all()])

def get_book(isbn):
    """
    Returns a book model with its respective authors for a given isbn.

    :param isbm: The isbn of the book to get.
    :return: A book model with its authors.
    """

    book = Book.query.filter_by(isbn=isbn).first()
    if book:
        return book

    book_data = get_book_data(isbn)
    author_data = book_data.pop('authors')
    book_data['published_date'] = book_data.pop('publishedDate')
    book_data['small_thumbnail'] = book_data.pop('smallThumbnail')

    book = Book(**book_data)
    for author in author_data:
        data = author.split(' ')
        if len(data) == 2:
            first, last = data
        elif len(data) == 3:
            first, last = (data[0], data[2])

        if Author.query.filter_by(first_name=first, last_name=last).first():
            continue

        book.authors.append(Author(first_name=first, last_name=last))

    return book

def get_book_data(isbn):
    """
    Get the book data for a given ISBN from the Google books API.

    :param isbn: The isbn of the book to get the data for.
    :return: An dictionary representing the book.
    """

    api_url = 'https://www.googleapis.com/books/v1/volumes?q=isbn:{}'
    response = requests.get(api_url.format(isbn))
    data = response.json()['items'][0]

    book_data = {
        'title': data['volumeInfo']['title'],
        'subtitle': data['volumeInfo'].get('subtitle', None),
        'authors': data['volumeInfo']['authors'],
        'isbn': isbn,
        'publisher': data['volumeInfo']['publisher'],
        'publishedDate': data['volumeInfo']['publishedDate'],
        'description': data['volumeInfo']['description'],
        'smallThumbnail': data['volumeInfo']['imageLinks']['smallThumbnail'],
        'thumbnail': data['volumeInfo']['imageLinks']['thumbnail']
    }

    return book_data
