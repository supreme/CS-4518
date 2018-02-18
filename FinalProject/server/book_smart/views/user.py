"""
user.py

Contains all API endpoints for interfacing with a user.

Created by Stephen Andrews, Februrary 17, 2018.
"""

from flask import Blueprint, request, jsonify

from book_smart.models import User
from book_smart.extensions import db


user = Blueprint('user', __name__)

@user.route('/login', methods=['POST'])
def login():
    """Attempt to log a user in."""

    username = request.form['username']
    password = request.form['password']

    account = User.query.filter_by(username=username).first()
    if not account or account.password != password:
        return jsonify({'status': 'error', 'message': 'Incorrect username or password!'}), 401

    return jsonify({'status': 'ok', 'message': 'Successfully logged in!'}), 200

@user.route('/register', methods=['POST'])
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

    account = User(username=username, password=password, first_name=first_name, last_name=last_name)
    db.session.add(account)
    db.session.commit()

    return jsonify({'status': 'ok', 'message': 'Successfully registered {}'.format(username)}), 200
