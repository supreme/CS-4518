"""
config.py

Contains configuration constants used throughout the app.

Created by Stephen Andrews, February 17th, 2018.
"""

import os


BASE_DIR = os.path.abspath(os.path.dirname(__file__))
DB_FILE = 'sqlite:///{}'.format(os.path.join(BASE_DIR, '..', 'book_smart.db'))
