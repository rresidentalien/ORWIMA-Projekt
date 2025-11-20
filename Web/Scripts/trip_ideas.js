let one = document.getElementById('one');
let two = document.getElementById('two');
let four = document.getElementById('four');
let six = document.getElementById('six');
let eight = document.getElementById('eight');
let night = document.getElementById('night');

one.style.display = 'none';
two.style.display = 'none';
four.style.display = 'none';
six.style.display = 'none';
eight.style.display = 'none';
night.style.display = 'none';

let hours = document.getElementById('hours');

function Search() {
    let value = hours.value;
    if (value == 'one') {
        one.style.display = 'block';
        two.style.display = 'none';
        four.style.display = 'none';
        six.style.display = 'none';
        eight.style.display = 'none';
        night.style.display = 'none';
    }
    if (value == 'two') {
        one.style.display = 'none';
        two.style.display = 'block';
        four.style.display = 'none';
        six.style.display = 'none';
        eight.style.display = 'none';
        night.style.display = 'none';
    }
    if (value == 'four') {
        one.style.display = 'none';
        two.style.display = 'none';
        four.style.display = 'block';
        six.style.display = 'none';
        eight.style.display = 'none';
        night.style.display = 'none';
    }
    if (value == 'six') {
        one.style.display = 'none';
        two.style.display = 'none';
        four.style.display = 'none';
        six.style.display = 'block';
        eight.style.display = 'none';
        night.style.display = 'none';
    }
    if (value == 'eight') {
        one.style.display = 'none';
        two.style.display = 'none';
        four.style.display = 'none';
        six.style.display = 'none';
        eight.style.display = 'block';
        night.style.display = 'none';
    }
    if (value == 'night') {
        one.style.display = 'none';
        two.style.display = 'none';
        four.style.display = 'none';
        six.style.display = 'none';
        eight.style.display = 'none';
        night.style.display = 'block';
    }
}