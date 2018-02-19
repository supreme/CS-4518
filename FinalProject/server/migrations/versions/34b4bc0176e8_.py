"""empty message

Revision ID: 34b4bc0176e8
Revises: 
Create Date: 2018-02-19 14:26:50.883219

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '34b4bc0176e8'
down_revision = None
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('author',
    sa.Column('author_id', sa.Integer(), nullable=False),
    sa.Column('first_name', sa.Text(), nullable=False),
    sa.Column('last_name', sa.Text(), nullable=False),
    sa.PrimaryKeyConstraint('author_id'),
    sa.UniqueConstraint('first_name'),
    sa.UniqueConstraint('last_name')
    )
    op.create_table('book',
    sa.Column('isbn', sa.Text(), nullable=False),
    sa.Column('description', sa.Text(), nullable=False),
    sa.Column('publisher', sa.Text(), nullable=False),
    sa.Column('published_date', sa.Integer(), nullable=False),
    sa.Column('title', sa.Text(), nullable=False),
    sa.Column('thumbnail', sa.Text(), nullable=False),
    sa.Column('small_thumbnail', sa.Text(), nullable=False),
    sa.Column('subtitle', sa.Text(), nullable=False),
    sa.PrimaryKeyConstraint('isbn'),
    sa.UniqueConstraint('isbn')
    )
    op.create_table('user',
    sa.Column('user_id', sa.Integer(), nullable=False),
    sa.Column('username', sa.Text(), nullable=False),
    sa.Column('password', sa.Text(), nullable=False),
    sa.Column('first_name', sa.Text(), nullable=False),
    sa.Column('last_name', sa.Text(), nullable=False),
    sa.PrimaryKeyConstraint('user_id'),
    sa.UniqueConstraint('first_name'),
    sa.UniqueConstraint('last_name'),
    sa.UniqueConstraint('username')
    )
    op.create_table('author_identifier',
    sa.Column('book_isbn', sa.Text(), nullable=True),
    sa.Column('author_id', sa.Integer(), nullable=True),
    sa.ForeignKeyConstraint(['author_id'], ['author.author_id'], ),
    sa.ForeignKeyConstraint(['book_isbn'], ['book.isbn'], )
    )
    op.create_table('owned_identifier',
    sa.Column('user_id', sa.Integer(), nullable=True),
    sa.Column('isbn', sa.Text(), nullable=True),
    sa.ForeignKeyConstraint(['isbn'], ['book.isbn'], ),
    sa.ForeignKeyConstraint(['user_id'], ['user.user_id'], )
    )
    op.create_table('wanted_identifier',
    sa.Column('user_id', sa.Integer(), nullable=True),
    sa.Column('isbn', sa.Text(), nullable=True),
    sa.ForeignKeyConstraint(['isbn'], ['book.isbn'], ),
    sa.ForeignKeyConstraint(['user_id'], ['user.user_id'], )
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('wanted_identifier')
    op.drop_table('owned_identifier')
    op.drop_table('author_identifier')
    op.drop_table('user')
    op.drop_table('book')
    op.drop_table('author')
    # ### end Alembic commands ###