import unittest

import requests


API = 'http://localhost:8080{endpoint}'

class TestAPI(unittest.TestCase):

    def user_creation_ok(self):
        response = requests.post(API.format(endpoint='/users/register'),
                                 data={'username': 'steve',
                                       'password': 'test',
                                       'firstName': 'Stephen',
                                       'lastName': 'Andrews'})
        assert response.status_code == 200

    def user_creation_fail(self):
        response = requests.post(API.format(endpoint='/users/register'),
                                 data={'username': 'steve',
                                       'password': 'test',
                                       'firstName': 'Stephen',
                                       'lastName': 'Andrews'})
        assert response.status_code == 409


    def user_login_ok(self):
        response = requests.post(API.format(endpoint='/users/login'),
                                 data={'username': 'steve',
                                       'password': 'test'})
        assert response.status_code == 200

    def user_login_fail(self):
        response = requests.post(API.format(endpoint='/users/login'),
                                 data={'username': 'steve',
                                       'password': 'test1'})
        assert response.status_code == 401

    def user_add_owned(self):
        """Adds a owned book to the user's profile."""
        response = requests.post(API.format(endpoint='/users/owned'),
                                 data={'username': 'steve',
                                       'isbn': '0321804333'})
        assert response.status_code == 200

    def user_add_wanted(self):
        """Adds a wanted book to the user's profile."""
        response = requests.post(API.format(endpoint='/users/wanted'),
                                 data={'username': 'steve',
                                       'isbn': '0321804333'})
        assert response.status_code == 200

    def add_listing(self):
        """Add a listing."""
        response = requests.post(API.format(endpoint='/listings/add'),
                                 data={'username': 'steve',
                                       'isbn': '0321804333',
                                       'listing_type': 'sale',
                                       'condition': 'excellent'})
        assert response.status_code == 200


    def test_all(self):
        #self.user_creation_ok()
        self.user_creation_fail()
        self.user_login_ok()
        self.user_login_fail()
        self.user_add_owned()
        self.user_add_wanted()
        self.add_listing()


if __name__ == '__main__':
    unittest.main()
