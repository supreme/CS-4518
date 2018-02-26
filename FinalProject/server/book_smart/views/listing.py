"""
listing.py

Contains all API endpoints for interfacing with a listing.

Created by Stephen Andrews, Februrary 20, 2018.
"""

from flask import Blueprint, request, jsonify, url_for

from book_smart.models import Listing, ListingType, User
from book_smart.extensions import db


listing_view = Blueprint('listing', __name__)

@listing_view.route('/', methods=['GET'])
@listing_view.route('/<listing_id>', methods=['GET'])
def get(listing_id=None):
    """Get a single listing or all the listings."""

    if listing_id:
        return jsonify(Listing.query.filter_by(listing_id=listing_id).first().to_json()), 200

    listings = [l.to_json() for l in Listing.query.all()]
    return jsonify(listings), 200

@listing_view.route('/add', methods=['POST'])
def add():
    """Create a new listing."""

    username = request.form['username']
    isbn = request.form['isbn']
    condition = request.form['condition']
    price = request.form.get('price', None)
    listing_types = request.form['listingTypes']

    import json
    print(json.dumps(request.form, indent=4))
    user = User.query.filter_by(username=username).first()
    # if isbn not in [book.isbn for book in user.owned_list]:
    #     return jsonify({'status': 'error', 'message': 'User  doesn\'t own book!'}), 401

    listing = Listing(username=username, isbn=isbn, condition=condition,
                      price=price)

    for listing_type in listing_types:
        l_type = ListingType.query.filter_by(listing_type_id=listing_type).first()
        if l_type:
            listing.listing_types.append(l_type)

    db.session.add(listing)
    db.session.commit()

    return 'success', 200

@listing_view.route('/delete/<listing_id>', methods=['GET'])
def delete(listing_id):
    """Delete a listing."""

    listing = Listing.query.filter_by(listing_id=listing_id).first()
    if listing:
        db.session.delete(listing)
        db.session.commit()

        return 'success', 200
