"""
server.py

Created by Stephen Andrews, February 17th, 2018.
"""

import os

from book_smart import create_app

app = create_app()
port = int(os.environ.get('PORT', default=8080))
debug = os.environ.get('DEBUG', default=False)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=port, debug=debug)
