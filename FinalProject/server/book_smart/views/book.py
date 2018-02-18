"""
book.py

Contains all API endpoints for interfacing with a book.

Created by Stephen Andrews, Februrary 17, 2018.
"""

from flask import Blueprint, jsonify
import requests

from book_smart.models import Author, Book
from book_smart.extensions import db


book = Blueprint('book', __name__)

@book.route('/', methods=['GET'])
@book.route('/<isbn>', methods=['GET'])
def get(isbn=None):
    """Get a book's information by its ISBN-10 identifier."""

    if isbn:
        return jsonify(get_book(isbn))

    return jsonify([b.to_json() for b in Book.query.all()])

def get_book(isbn):
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
        'subtitle': data['volumeInfo']['subtitle'],
        'authors': data['volumeInfo']['authors'],
        'publisher': data['volumeInfo']['publisher'],
        'publishedDate': data['volumeInfo']['publishedDate'],
        'description': data['volumeInfo']['description'],
        'smallThumbnail': data['volumeInfo']['imageLinks']['smallThumbnail'],
        'thumbnail': data['volumeInfo']['imageLinks']['thumbnail']
    }

    return book_data
