describe('check frontend', () => {
    it('checks', () => {
        //open homepage
        cy.visit('http://localhost:8080/');

        //Find text field and insert 1569319014
        cy.get('#isbnInput').type('1569319014');
        //submit
        cy.get('form').submit();

        //check new url
        cy.url().should('include', '/v1/books/1569319014');

        //check if the title is visible
        cy.contains('One Piece').should('be.visible');
    })
  })
