from app import app
with app.test_client() as c:
    response = c.get('/')
    assert response.data == b'<h1>Welcome to the Accounts API!</h1>"'
    assert response.status_code == 200