from flask import Flask
from flask_marshmallow import Marshmallow, fields
from flask import request, json, jsonify, Blueprint, redirect, url_for
from flask_sqlalchemy import SQLAlchemy
import os
import pymysql
import traceback
import decimal
#from Scripts.utils import *

pymysql.install_as_MySQLdb()

app = Flask(__name__, instance_relative_config=False)
app.config.from_object('config.DevelopmentConfig')

#plugins
db = SQLAlchemy(app)
ma = Marshmallow(app)

#set encoder
class MyJSONEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, decimal.Decimal):
            # Convert decimal instances to strings.
            return str(obj)
        return super(MyJSONEncoder, self).default(obj)

app.json_encoder = MyJSONEncoder

#models
class User(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    username = db.Column(db.String(200))
    accounts = db.relationship("Account", backref="user", lazy=False)
    #activity_types = db.relationship("ActivityType", backref="user", lazy=False)
    #job_roles = db.relationship("JobRole", backref="user", lazy=False)

class Account(db.Model):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(100), nullable=False)# Account Name    
    ip_domain = db.Column(db.String(60), nullable=False)# IP Domain   
    city = db.Column(db.String(60), nullable=True)# IP Geo City 
    state = db.Column(db.String(60), nullable=True)# IP Geo State    
    country = db.Column(db.String(60), nullable=True)# IP Geo Country  
    program = db.Column(db.String(100))
    salesforce_id = db.Column(db.String(60), unique=True, nullable=True)
    account_type = db.Column(db.String(60))
    activity_source = db.Column(db.String(30))
    user_id = db.Column(db.Integer(), db.ForeignKey("user.id"), nullable=True)
    opp_type = db.Column(db.String(30))
    mqa_score = db.Column(db.Numeric(12,5, asdecimal=True), default=0.0)
    marketing_qualified = db.Column(db.String(30))
    #opportunities = db.relationship("Opportunity", backref="account", lazy=False)
    #buyers = db.relationship("Buyer", backref="account", lazy=False)
    ocxid = db.Column(db.String(64))
    
# class Opportunity(db.Model):
#     id = db.Column(db.Integer(), primary_key=True)
#     account_id = db.Column(db.Integer(), db.ForeignKey("account.id"), nullable=True)   
#     contact_source = db.Column(db.String(60), nullable=True)
#     owner = db.Column(db.String(60), nullable=True)
#     name = db.Column(db.String(100), nullable=False)   
#     salesforce_id = db.Column(db.String(60), nullable=True)
#     lead_details = db.Column(db.String(300), nullable=True)    
#     lead_source = db.Column(db.String(60), nullable=True)
#     event_source = db.Column(db.String(60), nullable=True)
#     customer_type = db.Column(db.String(60), nullable=True)
#     fiscal_period = db.Column(db.String(60), nullable=True)
#     product_type = db.Column(db.String(60), nullable=True)
#     end_stage = db.Column(db.String(60), nullable=False)
#     pipeline_category = db.Column(db.String(60), nullable=True)
#     opp_type = db.Column(db.String(60), nullable=True)
#     tcv_amount = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     created_date = db.Column(db.DateTime(timezone=True))
#     close_date = db.Column(db.DateTime(timezone=True))
#     arr_additional = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     arr_new = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     arr_total = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     arr_multiyear = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     arr_renewal = db.Column(db.Numeric(12,2, asdecimal=True), nullable=True)   
#     tier = db.Column(db.String(100), nullable=False)   
#     infrastructure_provider = db.Column(db.String(100), nullable=True)
#     opportunity_stages = db.Column(db.relationship("OpportunityStage", backref="opportunity", lazy=False))

# stages = ["Qualification", "Discovery", "Demo", "Scoping", "Prep", "Install", "Execution", "Procurement", "Closed Won", "Booked", "Billing", "Closed Lost"]
# class OpportunityStage(db.Model):
#     id = db.Column(db.Integer(), primary_key=True)
#     opportunity_id = db.Column(db.Integer(), db.ForeignKey("opportunity.id"), nullable=True)
#     stage_name = db.Column(db.String(60), nullable=True)    
#     date = db.Column(db.DateTime(timezone=True))

#json schemas
class AccountSchema(ma.SQLAlchemyAutoSchema):
      class Meta:
            model = Account
            load_instance = True
            #include_fk = True
#routes
routes = Blueprint('routes', __name__, template_folder='templates')
POSTMAN_URL = 'http://www.google.com'
@routes.route("/", methods=["GET"])
def home():
    return (
        f"<h1>Welcome to the Accounts API!</h1>"
    )

def general_query(f):
    def q():
        request.parameter_storage_class = dict
        return f()
    q.__name__ = f.__name__
    return q

def all_query_wrapper(f):
    def wrapper(*args, **kwargs):
        try:
            query, schema, *extras = f(*args, **kwargs)
            return jsonify(data=schema.dump(query), extras=extras), 200
        except Exception as e:
            traceback.print_exc()
            return f'An Error Occured: {e}', 500
    wrapper.__name__ = f.__name__
    return wrapper

@routes.route("/accounts", methods=["GET"])
@general_query
@all_query_wrapper
def general_accounts_query():
    account_schema = AccountSchema(many=True)
    page = -1
    print(request.args)
    if 'page' in request.args:
        page = int(request.args['page'])
        del request.args['page']
    accounts = Account.query.filter_by(**request.args)

    if (page > 0):
        items = accounts.paginate(page=page, per_page=15, error_out=False).items
        return items, account_schema
    return accounts, account_schema

app.register_blueprint(routes)

if __name__ == "__main__":
    app.run(host='0.0.0.0')