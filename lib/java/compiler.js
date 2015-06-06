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
        dest:'dest'

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
        path += '.'+ options.ext;
    }

    var stream = fs.createReadStream(PATH.resolve(cwd,path), {encoding: 'utf8'})
        .pipe(through(function (trunk, type, next) {
            next(null, processChunk(trunk));
        }, function (done) {
            done();
        }));

    var writeAbsPath=PATH.resolve(options.dest,packageNameArr.join('/'));
    console.log(writeAbsPath)
    fse.ensureDir(writeAbsPath,function(err){
        if(err){
            console.error(err);
        }else{
            var writeStream = fs.createWriteStream(PATH.resolve(writeAbsPath,className+'.java'));
            writeStream.on('finish',function(){
                console.log('end');
            })
            stream.pipe(writeStream);
        }
    })


    function processChunk(trunk) {
        var i = 0, lim = trunk.length;
        _.each(trunk, function (char) {
            console.log(char)
        })
    }

}
module.exports = compile;