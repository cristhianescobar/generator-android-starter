'use strict';
var yeoman = require('yeoman-generator');
var chalk = require('chalk');
var yosay = require('yosay');
var path = require('path');

/**
 * Functionally the same as directory however applies templating if file name begins with an underscore (_).
 *
 * @param source
 * @param destination
 */
function templateDirectory(source, destination) {
  var root = this.isPathAbsolute(source) ? source : path.join(this.sourceRoot(), source);
  var files = this.expandFiles('**', { dot: true, cwd: root });

  for (var i = 0; i < files.length; i++) {
    var f = files[i];
    var src = path.join(root, f);
    if(path.basename(f).indexOf('_') == 0){
      var dest = path.join(destination, path.dirname(f), path.basename(f).replace(/^_/, ''));
      this.template(src, dest);
    }
    else{
      var dest = path.join(destination, f);
      this.copy(src, dest);
    }
  }
}

module.exports = yeoman.generators.Base.extend({
  initializing: function () {
    this.pkg = require('../package.json');
    this.templateDirectory = templateDirectory.bind(this);
  },

  prompting: function () {
    var done = this.async();

    // Have Yeoman greet the user.
    this.log(yosay(
      'Welcome to the rad ' + chalk.red('Android Square Stack') + ' generator!'
    ));

    var prompts = [{
      name: 'name',
      message: 'What are you calling your app?',
      store: true,
      default : this.appname // Default to current folder name
    },
    {
      name: 'package',
      message: 'What package will you be publishing the app under?',
      store: true
    },
    {
      name: 'targetSdk',
      message: 'What Android SDK will you be targeting?',
      store: true,
      default: 21  // Android 5.0 (Lollipop)
    },
    {
      name: 'minSdk',
      message: 'What is the minimum Android SDK you wish to support?',
      store: true,
      default: 14  // Android 4.0 (Ice Cream Sandwich)
    }];

    this.prompt(prompts, function (props) {
      this.appName = props.name;
      this.appPackage = props.package;
      this.androidTargetSdkVersion = props.targetSdk;
      this.androidMinSdkVersion = props.minSdk;

      done();
    }.bind(this));
  },

  writing: {
    projectfiles: function () {
      this.copy('gitignore', '.gitignore');
      this.copy('build.gradle', 'build.gradle');
      this.copy('gradle.properties', 'gradle.properties');
      this.copy('gradlew', 'gradlew');
      this.copy('gradlew.bat', 'gradlew.bat');
      this.copy('settings.gradle', 'settings.gradle');
      this.template('_README.md', 'README.md');
      this.directory('gradle', 'gradle');
    },

    app: function () {
      var packageDir = this.appPackage.replace(/\./g, '/');

      this.mkdir('app');
      this.copy('app/gitignore', 'app/.gitignore');
      this.copy('app/proguard-rules.pro', 'app/proguard-rules.pro');
      this.template('app/_build.gradle', 'app/build.gradle');

      this.mkdir('app/src/androidTest/java/' + packageDir);
      this.templateDirectory('app/src/androidTest/java', 'app/src/androidTest/java/' + packageDir);
      this.templateDirectory('app/src/androidTest/res', 'app/src/androidTest/res');

      this.mkdir('app/src/env_prod/java/' + packageDir);
      this.templateDirectory('app/src/env_prod/java', 'app/src/env_prod/java/' + packageDir);

      this.mkdir('app/src/env_test/java/' + packageDir);
      this.templateDirectory('app/src/env_test/java', 'app/src/env_test/java/' + packageDir);
      this.templateDirectory('app/src/env_test/res', 'app/src/env_test/res');

      this.mkdir('app/src/main/assets');
      this.mkdir('app/src/main/java/' + packageDir);
      this.directory('app/src/main/assets', 'app/src/main/assets');
      this.template('app/src/main/_AndroidManifest.xml', 'app/src/main/AndroidManifest.xml');
      this.templateDirectory('app/src/main/java', 'app/src/main/java/' + packageDir);
      this.templateDirectory('app/src/main/res', 'app/src/main/res');
    }
  }
});
