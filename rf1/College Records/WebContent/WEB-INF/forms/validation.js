function trim(s) {
    return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
}

function signupValidate()
{
var uname = document.form.username;
var pass = document.form.password;
var fname = document.form.firstname;
var lname = document.form.lastname;
var ver = document.form.verify;
var em = document.form.email;
var p = document.form.phone;
var z = document.form.zip;
var c = document.form.city;
var s = document.form.street;

if(username_validation(uname) == false)
{
	alert("That's not a valid username.");
	return false;
}
if(password_validation(pass) == false)
{
	alert("That wasn't a valid password.");
	pass.value="";
	ver.value="";
	return false;
}
if(ver.value != pass.value)
{
	alert("Your passwords didn't match.");
	pass.value="";
	ver.value="";
	return false;
}

if(firstname_validation(fname) == false)
{
	alert("That's not a valid firstname.");
	return false;
}

if(lastname_validation(lname) == false)
{
	alert("That's not a valid lastname.");
	return false;
}

if(email_validation(em) == false)
{
	alert("That's not a valid email.");
	return false;
}

if(phone_validation(p) == false)
{
	alert("That's not a valid phone number.\nTry: xx-xx-xxx-xxxx or xxxxxxxxxx");
	return false;
}

if(zip_validation(z) == false)
{
	alert("That's not a valid zip code.\nTry: xxxx");
	return false;
}

if(city_validation(c) == false)
{
	alert("That's not a valid city name.");
	return false;
}

if(street_validation(s) == false)
{
	alert("That's not a valid street name.");
	return false;
}

return true;
}

function editUserValidate()
{
var pass = document.form.password;
var fname = document.form.firstname;
var lname = document.form.lastname;
var ver = document.form.verify;
var em = document.form.email;
var p = document.form.phone;
var z = document.form.zip;
var c = document.form.city;
var s = document.form.street;
var r = document.form.room;

if(pass.value.length != 0 && password_validation(pass) == false)
{
	alert("That wasn't a valid password.\n");
	pass.value="";
	ver.value="";
	return false;
}

if( (ver.value.length != 0 && pass.value.length != 0 ) &&  ver.value != pass.value)
{
	alert("Your passwords didn't match.");
	pass.value="";
	ver.value="";
	return false;
}

if(firstname_validation(fname) == false)
{
	alert("That's not a valid firstname.");
	return false;
}

if(lastname_validation(lname) == false)
{
	alert("That's not a valid lastname.");
	return false;
}

if(email_validation(em) == false)
{
	alert("That's not a valid email."+em.value);
	return false;
}

if(phone_validation(p) == false)
{
	alert("That's not a valid phone number.\nTry: xx-xx-xxx-xxxx or xxxxxxxxxx");
	return false;
}

if(zip_validation(z) == false)
{
	alert("That's not a valid zip code.\nTry: xxxx");
	return false;
}

if(city_validation(c) == false)
{
	alert("That's not a valid city name.");
	return false;
}

if(street_validation(s) == false)
{
	alert("That's not a valid street name.");
	return false;
}

if(room_validation(r) == false)
{
	alert("That's not a valid room number.");
	return false;
}

return true;
}

function editNewsValidate()
{

var ti = trim(document.form.title.value);
var co = trim(document.form.content.value);

if(ti == "" || co == "")
{
	alert("Need to fill both.");
	return false;
}

return true;
}

function editEventValidate()
{
var ti = trim(document.form.title.value);
var co = trim(document.form.content.value);
var d = document.form.date;

if(ti == "" || co == "")
{
	alert("Need to fill title and content.");
	return false;
}

if(date_validation(d) == false)
{
	alert("That's not a valid date.");
	return false;
}

return true;
}

function loginValidate()
{
var uname = document.form.username;

if(username_validation(uname) == false)
{
	alert("That's not a valid username.");
	return false;
}

return true;
}

function CommentValidate()
{
var text = trim(document.form.text.value);

if(text == "")
{
	alert("Need to fill the text.");
	return false;
}

return true;
}

function editBalanceValidate()
{
var uname = document.form.username;
var a = document.form.amount;
var d = document.form.description;
var dl = document.form.deadline;
var pd = document.form.paydate;

if(username_validation(uname) == false)
{
	alert("That's not a valid username.");
	return false;
}

if(amount_validation(a) == false)
{
	alert("That's not a valid amount.");
	return false;
}

if(description_validation(d) == false)
{
	alert("That's not a valid description.");
	return false;
}

if(date_validation(dl) == false)
{
	alert("That's not a valid deadline.");
	return false;
}

if(pd.value.length != 0 && date_validation(pd) == false)
{
	alert("That's not a valid paydate.");
	return false;
}

if(date_validation(pd) == true){
	document.form.paid.value="true";
}else{
	document.form.paid.value="false";
}

return true;
}

function username_validation(username)
{
var username_re = /^[a-zA-Z0-9_-]{3,15}$/;
if(username.value.match(username_re))
{
return true;
}
return false;
}

function password_validation(password)
{
var password_re = /^[a-zA-Z@#$%!_]{3,20}$/;
//"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"
if(password.value.match(password_re))
{
return true;
}
return false;
}


function verify_validation(verify,password)
{
var verify_re = password;
if(verify.value.match(verify_re))
{
return true;
}
return false;
}

function firstname_validation(firstname)
{
var firstname_re = /^[a-zA-ZáéóöőúüűÁÉÓÖŐÚÜŰ]{2,20}$/;
if(firstname.value.match(firstname_re))
{
return true;
}
return false;
}

function lastname_validation(lastname)
{
var lastname_re = /^[a-zA-ZáéóöőúüűÁÉÓÖŐÚÜŰ]{2,20}$/;
if(lastname.value.match(lastname_re))
{
return true;
}
return false;
}

function email_validation(email)
{
var email_re = /^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))$/;
if(email.value.match(email_re))
{
return true;
}
return false;
}

function phone_validation(phone)
{
var phone_re = /^0[1-9]{1}[\s]{0,1}[\-]{0,1}[\s]{0,1}[237]{1}0[\s]{0,1}[\-]{0,1}[\s]{0,1}[1-9]{1}[0-9]{2}[\s]{0,1}[\-]{0,1}[\s]{0,1}[0-9]{4}$/;
if(phone.value.match(phone_re))
{
return true;
}
}

function zip_validation(zip)
{
var zip_re = /^[1-9]{1}[0-9]{3}$/;
if(zip.value.match(zip_re))
{
return true;
}
return false;
}

function city_validation(city)
{
var city_re = /^[a-zA-ZáéóöőúüűÁÉÓÖŐÚÜŰ]{2,20}$/;
if(city.value.match(city_re))
{
return true;
}
return false;
}

function street_validation(street)
{
var street_re = /^[a-zA-ZáéóöőúüűÁÉÓÖŐÚÜŰ 0-9\.]{2,30}$/;
if(street.value.match(street_re))
{
return true;
}
return false;
}

function room_validation(room)
{
var room_re = /^[1-9]{1}[0-9]{0,3}$/;
if(room.value.match(room_re))
{
return true;
}
return false;
}

function date2_validation(date)
{
//var date_re = /(^19[789]{1}[0-9]{1}[\-]{1}((1[013]{1})|(0[1-9]{1}))[\-]{1}((0[1-9]{1})|([12]{1}[0-9]{1})|(3[01]{1}))$)|(^20[0-9]{2}$)/;
var date_re =	/^(?:(?:(?:0?[13578]|1[02])(\/|-|\.)31)\1|(?:(?:0?[13-9]|1[0-2])(\/|-|\.)(?:29|30)\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:0?2(\/|-|\.)29\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9])|(?:1[0-2]))(\/|-|\.)(?:0?[1-9]|1\d|2[0-8])\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
if(date.value.match(date_re))
{
return true;
}
return false;
}

function date_validation(date)
{
	var date_re =	/^(19|20)\d\d([-])(0[1-9]|1[012])([-])(0[1-9]|[12][0-9]|3[01])$/;
	if(date.value.match(date_re))
	{
		/*// At this point, $1 holds the year, $2 the month and $3 the day of the date entered
	    if ($3 == 31 and ($2 == 4 or $2 == 6 or $2 == 9 or $2 == 11)) {
	      return 0; // 31st of a month with 30 days
	    } elsif ($3 >= 30 and $2 == 2) {
	      return 0; // February 30th or 31st
	    } elsif ($2 == 2 and $3 == 29 and not ($1 % 4 == 0 and ($1 % 100 != 0 or $1 % 400 == 0))) {
	      return 0; // February 29th outside a leap year
	    } else {*/
	      return true; // Valid date
	    //}
	}
//invalid
return false;
}

function amount_validation(amount)
{
var amount_re = /^[0-9]{1,10}$/;
if(amount.value.match(amount_re))
{
return true;
}
return false;
}

function description_validation(description)
{
var description_re = /^[a-zA-ZáéóöőúüűÁÉÓÖŐÚÜŰ 0-9\.]{1,30}$/;
if(description.value.match(description_re))
{
return true;
}
return false;
}