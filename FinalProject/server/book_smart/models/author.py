"""
author.py

Created by Stephen Andrews, February 18th, 2018.
"""

from book_smart.extensions import db


class Author(db.Model):
    """Represents an author."""

    author_id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.Text, unique=True, nullable=False)
    last_name = db.Column(db.Text, unique=True, nullable=False)

    def to_json(self):
        author = {}
        author['author_id'] = self.author_id
        author['first_name'] = self.first_name
        author['last_name'] = self.last_name

        return author
