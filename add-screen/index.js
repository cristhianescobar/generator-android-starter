'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var path = require('path');

function upperCaseFirst( str ) {
    return str.substr(0, 1).toUpperCase() + str.substr(1);
}

module.exports = yeoman.generators.Base.extend({
  initializing: function () {
    this.pkg = require('../package.json');
    this.appPackage = this.config.get('appPackage');
  },

  prompting: function () {
    var done = this.async();

    var prompts = [{
      name: 'screenName',
      message: 'What is the new screen name?',
      store: true,
      default : "Something"
    }];

    this.prompt(prompts, function (props) {
      this.screenName = upperCaseFirst(props.screenName);
      this.screenPackage = props.screenName.toLowerCase();
      done();
    }.bind(this));
  },

  writing: {
    app: function () {
      var packageDir = this.appPackage.replace(/\./g, '/');

      var screenPath = 'app/src/main/java/' + packageDir + '/screen/' + this.screenPackage;
      var testScreenPath = 'app/src/androidTest/java/' + packageDir + '/screen/' + this.screenPackage;
      this.mkdir(screenPath);
      this.template('app/src/main/java/screen/newscreen/_Presenter.java', screenPath + '/' + this.screenName + 'Presenter.java');
      this.template('app/src/main/java/screen/newscreen/_Screen.java', screenPath + '/' + this.screenName + 'Screen.java');
      this.template('app/src/main/java/screen/newscreen/_View.java', screenPath + '/' + this.screenName + 'View.java');
      this.template('app/src/main/res/layout/_screen.xml', 'app/src/main/res/layout/screen_' + this.screenPackage + '.xml');

      this.template('app/src/androidTest/java/screen/_ScreenTest.java', testScreenPath + '/' + this.screenName + 'ScreenTest.java');

    }
  }
});
