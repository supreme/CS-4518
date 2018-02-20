"""
transaction.py

Contains all API endpoints for interfacing with a transaction.

Created by Stephen Andrews, Februrary 20, 2018.
"""

from flask import Blueprint, request, jsonify, url_for

from book_smart.models import Transaction, User
from book_smart.extensions import db


transaction_view = Blueprint('transaction', __name__)

@transaction_view.route('/', methods=['GET'])
@transaction_view.route('/<transaction_id>', methods=['GET'])
def get(transaction_id=None):
    """Get a single transaction or all the transactions."""

    if transaction_id:
        return jsonify(Transaction.query.filter_by(transaction_id=transaction_id).first().to_json()), 200

    transactions = [t.to_json() for t in Transaction.query.all()]
    return jsonify(transactions), 200

@transaction_view.route('/add', methods=['POST'])
def add():
    """Create a new transaction."""

    buyer = request.form['buyer']
    seller = request.form['seller']
    listing_id = request.form['listing_id']
    status = request.form.get['status']

    transaction = Transaction(buyer=buyer, seller=seller, listing_id=listing_id, status=status)
    db.session.add(transaction)
    db.session.commit()

    return 'success', 200
