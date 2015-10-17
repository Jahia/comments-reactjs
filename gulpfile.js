var browserify = require('browserify'),
    watch = require('gulp-watch'),
    gulp = require('gulp'),
    source = require("vinyl-source-stream"),
    reactify = require('reactify'),
    del = require('del'),
    argv = require('yargs').argv;

if (argv.watch) {
  watch(['src/main/resources/javascript/**/*.jsx'], function() {
    gulp.start('browserify');
  });
}

gulp.task('default', ['browserify'], function() {
});

gulp.task('browserify', function () {
  // sync
  del.sync('src/main/resources/javascript/react/application.js');
  var b = browserify();
  b.transform(reactify); // use the reactify transform
  b.add('src/main/resources/javascript/react/application.jsx');
  return b.bundle()
    .pipe(source('application.js'))
    .pipe(gulp.dest('src/main/resources/javascript/react'));
});