"""
server.py

Created by Stephen Andrews, February 17th, 2018.
"""

from book_smart import create_app

app = create_app()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
