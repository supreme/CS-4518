"""
user.py

Created by Stephen Andrews, February 17th, 2018.
"""

from book_smart.extensions import db

owned_identifier = db.Table('owned_identifier',
    db.Column('user_id', db.Integer, db.ForeignKey('user.user_id')),
    db.Column('isbn', db.Text, db.ForeignKey('book.isbn'))
)

wanted_identifier = db.Table('wanted_identifier',
    db.Column('user_id', db.Integer, db.ForeignKey('user.user_id')),
    db.Column('isbn', db.Text, db.ForeignKey('book.isbn'))
)

class User(db.Model):
    """Represents a user."""

    user_id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.Text, unique=True, nullable=False)
    password = db.Column(db.Text, nullable=False)
    first_name = db.Column(db.Text, unique=True, nullable=False)
    last_name = db.Column(db.Text, unique=True, nullable=False)
    owned_list = db.relationship('Book', secondary=owned_identifier)
    wanted_list = db.relationship('Book', secondary=wanted_identifier)

    def to_json(self):
        user = {}
        user['user_id'] = self.user_id
        user['username'] = self.username
        user['firstName'] = self.first_name
        user['lastName'] = self.last_name
        user['owned'] = [book.to_json() for book in self.owned_list]
        user['wanted'] = [book.to_json() for book in self.wanted_list]

        return user
