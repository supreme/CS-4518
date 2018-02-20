"""
app.py

Creates the Book Smart backend.

Created by Stephen Andrews, February 17, 2018.
"""

from flask import Flask

from book_smart import config
from book_smart.extensions import db
from book_smart.views.book import book_view
from book_smart.views.listing import listing_view
from book_smart.views.user import user_view
from book_smart.views.transaction import transaction_view

def create_app():
    """Initialize the backend."""
    app = Flask('book_smart')
    app.config['SQLALCHEMY_DATABASE_URI'] = config.DB_FILE
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
    db.init_app(app)

    # Register blueprints
    app.register_blueprint(book_view, url_prefix='/books')
    app.register_blueprint(listing_view, url_prefix='/listings')
    app.register_blueprint(user_view, url_prefix='/users')
    app.register_blueprint(transaction_view, url_prefix='/transactions')

    return app
