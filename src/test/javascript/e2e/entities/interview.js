'use strict';

describe('Interview e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load Interviews', function () {
        entityMenu.click();
        element.all(by.css('[ui-sref="interview"]')).first().click().then(function() {
            expect(element.all(by.css('h2')).first().getText()).toMatch(/Interviews/);
        });
    });

    it('should load create Interview dialog', function () {
        element(by.css('[ui-sref="interview.new"]')).click().then(function() {
            expect(element(by.css('h4.modal-title')).getText()).toMatch(/Create or edit a Interview/);
            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
