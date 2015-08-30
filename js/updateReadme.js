/**
 * Created by Alireza Mirian (alireza.mirian@gmail.com) on 30/08/2015.
 */


var fs = require('fs'),
    xml2js = require('xml2js'),
    Q = require('Q'),
    parser = new xml2js.Parser();

var readFile = Q.nfbind(fs.readFile), // denodeify
    writeFile = Q.nfbind(fs.writeFile), // denodeify
    parseXml = Q.nfbind(parser.parseString), // denodeify
    templatesXmlFilePath = './angular-templates/resources/liveTemplates/Angular_Templates.xml',
    readMePath = './README.md';

module.exports = updateReadme;


function updateReadme(){
    return readReadMe().then(function(readMeContent){
        var startSymbol = '[comment]: # (templateDocs)',
            endSymbol = '[comment]: # (/templateDocs)';

        var startIdx = readMeContent.indexOf(startSymbol);
        var endIdx = readMeContent.indexOf(endSymbol);
        if(startIdx>-1 && endIdx > -1){
            return getTemplatesDescription().then(function(markdownToBeInserted){
                return readMeContent.substring(0, startIdx + startSymbol.length) + "\n\n"+
                    markdownToBeInserted + "\n\n"+
                    readMeContent.substring(endIdx);
            }).then(writeReadMe);
        }
        else{
            return Q.reject("There is no hole in readme to put template docs")
        }

    })

}

function writeReadMe(content){
    return writeFile(readMePath, content, 'utf-8');
}
function readReadMe(){
    return readFile(readMePath, 'utf-8');
}
function getTemplatesDescription(){


    return readFile(templatesXmlFilePath).then(parseXml).then(getMarkdownFromJsObj);
}

function getMarkdownFromJsObj(result){
    return result.templateSet.template.map(function(template){
        //console.log(template);
        return "- " + template.$.name + ": " + template.$.description;
    }).join("\n");
}