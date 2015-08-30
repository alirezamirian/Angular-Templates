/**
 * Created by Alireza Mirian (alireza.mirian@gmail.com) on 30/08/2015.
 */

var updateReadme = require("./js/updateReadme.js");

updateReadme().then(function(){
    console.info("Readme has been updated successfully");
});