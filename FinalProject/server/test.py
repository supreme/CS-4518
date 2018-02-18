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

    def test_user_creation(self):
        self.user_creation_ok()
        self.user_creation_fail()

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

    def test_user_login(self):
        self.user_login_ok()


if __name__ == '__main__':
    unittest.main()
