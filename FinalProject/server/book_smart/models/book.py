"""
book.py

Created by Stephen Andrews, February 17, 2018.
"""

from book_smart.extensions import db

author_identifier = db.Table('author_identifier',
    db.Column('book_isbn', db.Text, db.ForeignKey('book.isbn')),
    db.Column('author_id', db.Integer, db.ForeignKey('author.author_id'))
)

class Book(db.Model):
    """Represents a book."""

    isbn = db.Column(db.Text, primary_key=True, unique=True, nullable=False)
    authors = db.relationship('Author', secondary=author_identifier)
    description = db.Column(db.Text, nullable=False)
    publisher = db.Column(db.Text, nullable=False)
    published_date = db.Column(db.Integer, nullable=False)
    title = db.Column(db.Text, nullable=False)
    subtitle = db.Column(db.Text, nullable=False)

    def to_json(self):
        book = {}
        book['isbn'] = self.isbn
        book['authors'] = self.authors
        book['description'] = self.description
        book['publisher'] = self.publisher
        book['published_date'] = self.published_date
        book['title'] = self.title
        book['subtitle'] = self.subtitle

        return book
