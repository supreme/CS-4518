"""
listing.py

Created by Stephen Andrews, February 19th, 2018.
"""

from book_smart.extensions import db


class Listing(db.Model):
    """Represents one of three listings -- sale, loan, or swap."""

    listing_id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.Text, db.ForeignKey('user.username'), nullable=False)
    isbn = db.Column(db.Text, db.ForeignKey('book.isbn'), nullable=False)
    condition = db.Column(db.Text, nullable=False)
    price = db.Column(db.Float)
    listing_type = db.Column(db.Text, nullable=False)

    def to_json(self):
        listing = {}
        listing['listing_id'] = self.listing_id
        listing['username'] = self.username
        listing['isbn'] = self.isbn
        listing['condition'] = self.condition
        listing['price'] = self.price
        listing['listing_type'] = self.listing_type

        return listing
