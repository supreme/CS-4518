"""
user.py

Contains all API endpoints for interfacing with a user.

Created by Stephen Andrews, Februrary 17, 2018.
"""

from flask import Blueprint, request, jsonify, url_for
import requests

from book_smart.models import User
from book_smart.extensions import db
from book_smart.views.book import get_book


user_view = Blueprint('user', __name__)


@user_view.route('/<username>', methods=['GET'])
def get_user(username):
    """Get a user's profile."""

    return jsonify(User.query.filter_by(username=username).first().to_json()), 200

@user_view.route('/owned', methods=['POST'])
def add_owned_book():
    """Add a book to a user's 'owned' list."""

    username = request.form['username']
    isbn = request.form['isbn']

    user = User.query.filter_by(username=username).first()
    book = get_book(isbn)

    # Create relationships
    user.owned_list.append(book)
    db.session.add(user)
    db.session.commit()

    return 'success', 200

@user_view.route('/wanted', methods=['POST'])
def add_wanted_book():
    """Add a book to a user's 'wanted' list."""

    username = request.form['username']
    isbn = request.form['isbn']

    user = User.query.filter_by(username=username).first()
    book = get_book(isbn)

    # Create relationships
    user.wanted_list.append(book)
    db.session.add(user)
    db.session.commit()

    return 'success', 200

@user_view.route('/login', methods=['POST'])
def login():
    """Attempt to log a user in."""

    username = request.form['username']
    password = request.form['password']

    user = User.query.filter_by(username=username).first()
    if not user or user.password != password:
        return jsonify({'status': 'error', 'message': 'Incorrect username or password!'}), 401

    return jsonify({'status': 'ok', 'message': 'Successfully logged in!'}), 200

@user_view.route('/register', methods=['POST'])
def register():
    """Attempt to register a new user."""

    required_fields = ['username', 'password', 'firstName', 'lastName']
    for field in required_fields:
        if field not in request.form:
            return jsonify({'status': 'error', 'message': 'Missing required fields!'}), 412

    username = request.form['username']
    password = request.form['password']
    first_name = request.form['firstName']
    last_name = request.form['lastName']

    if User.query.filter_by(username=username).first():
        return jsonify({'status': 'error', 'message': 'Username already taken!'}), 409

    user = User(username=username, password=password, first_name=first_name, last_name=last_name)
    db.session.add(user)
    db.session.commit()

    return jsonify({'status': 'ok', 'message': 'Successfully registered {}'.format(username)}), 201
