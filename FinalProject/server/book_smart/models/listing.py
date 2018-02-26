"""
listing.py

Created by Stephen Andrews, February 19th, 2018.
"""

from book_smart.extensions import db

listing_identifier = db.Table('listing_identifier',
    db.Column('listing_id', db.Integer, db.ForeignKey('listing.listing_id')),
    db.Column('listing_type_id', db.Integer, db.ForeignKey('listing_type.listing_type_id'))
)

class ListingType(db.Model):

    listing_type_id = db.Column(db.Integer, primary_key=True)
    listing_type = db.Column(db.Text, nullable=False)


class Listing(db.Model):
    """Represents one of three listings -- sale, loan, or swap."""

    listing_id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.Text, db.ForeignKey('user.username'), nullable=False)
    isbn = db.Column(db.Text, db.ForeignKey('book.isbn'), nullable=False)
    condition = db.Column(db.Text, nullable=False)
    price = db.Column(db.Float)
    listing_types = db.relationship('ListingType', secondary=listing_identifier)

    def to_json(self):
        listing = {}
        listing['listingId'] = self.listing_id
        listing['username'] = self.username
        listing['isbn'] = self.isbn
        listing['condition'] = self.condition
        listing['price'] = self.price
        listing['listingTypes'] = [l.listing_type for l in self.listing_types]

        return listing
