"""
transaction.py

Created by Stephen Andrews, February 2018.
"""

from book_smart.extensions import db


class Transaction(db.Model):
    """Represents a transaction between two users."""

    transaction_id = db.Column(db.Integer, primary_key=True)
    buyer = db.Column(db.Text, db.ForeignKey('user.username'), nullable=False)
    seller = db.Column(db.Text, db.ForeignKey('user.username'), nullable=False)
    listing_id = db.Column(db.Integer, db.ForeignKey('listing.listing_id'), nullable=False)
    status = db.Column(db.Text, nullable=False)

    def to_json(self):
        transaction = {}
        transaction['transaction_id'] = self.transaction_id
        transaction['buyer'] = self.buyer
        transaction['seller'] = self.seller
        transaction['listing_id'] = self.listing_id
        transaction['status'] = self.status

        return transaction
