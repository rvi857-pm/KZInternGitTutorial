class BaseConfig(object):
    DEBUG = False
    TESTING = False
    SQLALCHEMY_TRACK_MODIFICATIONS = True

class DevelopmentConfig(BaseConfig):
    DEBUG = True
    TESTING = True
    SQLALCHEMY_DATABASE_URI = "mysql://root:Ri*13mrk@127.0.0.1/unravel_data?charset=utf8mb4"


class TestingConfig(BaseConfig):
    DEBUG = False
    TESTING = True
    SQLALCHEMY_DATABASE_URI = "mysql://root:Ri*13mrk@127.0.0.1/unravel_data?charset=utf8mb4"


class DeploymentConfig(BaseConfig):
    DEBUG = False
    TESTING = False