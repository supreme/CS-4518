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
    thumbnail = db.Column(db.Text, nullable=False)
    small_thumbnail = db.Column(db.Text, nullable=False)
    subtitle = db.Column(db.Text)

    def to_json(self):
        book = {}
        book['isbn'] = self.isbn
        book['authors'] = ['{first_name} {last_name}'.format(**author.to_json()) for author in self.authors]
        book['description'] = self.description
        book['publisher'] = self.publisher
        book['publishedDate'] = self.published_date
        book['title'] = self.title
        book['thumbnail'] = self.thumbnail
        book['smallThumbnail'] = self.small_thumbnail
        book['subtitle'] = self.subtitle

        return book
