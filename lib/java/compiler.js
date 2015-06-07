var _ = require('lodash'),
    PATH = require('path'),
    Q = require('q'),
    fs = require('fs'),
    through = require('through2'),
    fse = require('fs-extra');

function compile(path, callback, options) {
    if (typeof path != 'string') {
        throw new Error('path parameter error: invalid path');
    }

    var oldCwd = process.cwd();
    options = _.merge({
        cwd: process.cwd(),
        ext: 'hbs',
        packagePrefix: ['lost', 'temple'],
        dest: 'dest',
        interfaceName: 'com.qunar.temple.TemplateInterface'

    }, options);
    var cwd = PATH.resolve(options.cwd);

    //get class and classname from path;
    var pathStrArr = path.split('/'),
        className = pathStrArr.splice(pathStrArr.length - 1)[0],
        packageNameArr = options.packagePrefix.concat(pathStrArr),
        packageName = packageNameArr.join('.');

    var dotIndex = className.indexOf('.');
    if (dotIndex >= 0) {
        className = className.slice(0, dotIndex);
    } else {
        path += '.' + options.ext;
    }

    var startStr = 'package '
        + packageName
        + ';\n\nimport java.io.StringWriter;\nimport java.lang.reflect.Method;\nimport java.util.Iterator;\nimport java.util.List;\nimport java.util.Map;\n\npublic class '
        + className
        + ' implements '
        + options.interfaceName
        + '{\n\tpublic String render(Map<String, Object> data) {\n\t\tStringWriter sw = new StringWriter();\n';


    var stream = fs.createReadStream(PATH.resolve(cwd, path), {encoding: 'utf8'})
        .pipe(through(function (trunk, type, next) {
            next(null, processChunk(trunk));
        }, function (done) {
            if (mode.is('writing')) {
                this.push('");');
            }
            this.push('\n\t\treturn sw.toString();\n\t}\n}');
            done();
        }));

    var writeAbsPath = PATH.resolve(options.dest, packageNameArr.join('/'));


    fse.ensureDir(writeAbsPath, function (err) {
        if (err) {
            console.error(err);
        } else {
            var writeStream = fs.createWriteStream(PATH.resolve(writeAbsPath, className + '.java'));
            writeStream.write(startStr);
            writeStream.on('finish', function () {
                if (callback instanceof Function) {
                    callback();
                }
            });
            stream.pipe(writeStream);
        }
    });


    function processChunk(trunk) {
        var i = 0, lim = trunk.length, result = '';
        _.each(trunk, function (charCode) {
            result += processCharCode(charCode) || '';
        });
        return result;
    }

    var lineNumber = 1,
        charNumber = 0,
        Mode = require('./Mode'),
        mode = new Mode();


    var CHAR_TAB = 9,//\t
        CHAR_NEW_LINE = 10,//\n
        CHAR_RETURN = 13,//\r
        CHAR_BLOCK = 35,//#
        CHAR_PATIAL = 62,//>
        CHAR_ESCAPE = 92,//\
        CHAR_OPEN_BRACE = 123,//{
        CHAR_CLOSE_BRACE = 125;//}

    function processCharCode(i) {
        if (i == CHAR_RETURN) {// if input is \r, do nothing.
            lastCharCode = i;
            return '';
        }
        var rtn;

        if (i == CHAR_ESCAPE && mode.not('directive')) {
            mode.ifis(maybeEscape)

            mode.enter('maybeEscape');
        } else if (i == CHAR_OPEN_BRACE) {
            if (mode.ifis('maybeEscape')) {
                mode.enter('escape');
            } else {
                if (mode.ifis('maybeDirective')) {
                    mode.enter('directive');
                    mode.enter('directiveLevel1');
                    if (mode.ifis('writing')) {

                    }
                } else if (mode.ifis('directiveLevel1')) {
                    mode.enter('directiveLevel2');
                } else {
                    mode.enter('maybeDirective');
                }
            }
        } else {
            mode.quit('escape');
        }


        if (mode.not('directive')) {
            var character;

            switch (i) {
                case CHAR_TAB:
                    character = '\\t';
                    break;
                case CHAR_NEW_LINE:
                    character = '\\n';
                    break;
                default:
                    character = char(i);
            }
            if (mode.not('writing')) {

                rtn = newLine() + 'sw.write("' + character;
                mode.enter('writing');
            } else {
                rtn = character;
            }
        }

        lastCharCode = i;
        return rtn;
    }

    function char(i) {
        return String.fromCharCode(i);
    }


    //generate heading tab in one line
    var tabs = [];

    function newLine() {
        var level = mode.level + 2;
        return tabs[level] || (tabs[level] = _.fill(new Array(level), '\t').join(''));
    }


}


module.exports = compile;