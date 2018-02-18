from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager

from book_smart import create_app
from book_smart.extensions import db
from book_smart.models import *

app = create_app()
migrate = Migrate(app, db)

manager = Manager(app)
manager.add_command('db', MigrateCommand)


if __name__ == "__main__":
    manager.run()
