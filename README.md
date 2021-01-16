# Welcome to the Git Tutorial for KZ Interns!

### Getting Started
1. **If you have Windows:** [download Git for Windows here](https://gitforwindows.org/), then open Git BASH.
2. **If you have Mac or Linux:** Open Terminal.
3. [Follow this tutorial to generate a public SSH key on your machine with a password.](https://git-scm.com/book/en/v2/Git-on-the-Server-Generating-Your-SSH-Public-Key)
4. [Add your public SSH key to your new github account using this link.](https://github.com/settings/keys)
5. Run the command `ssh-agent`. If you get back some info about a pid with a number, run the command `ssh-add` and input your password. This will allow you to clone from github without having to input your ssh password every time.
6. **Update your git email and username:**
```
$git config --global user.email = '<yourname>@kwanzoo.com'
$git config --global user.name = 'Your Name Here'
```
7. Clone this repo onto your home machine (in whichever folder you'd like):
```
$cd path/to/directory/of/choice
$git clone git@github.com:rvi857-pm/KZInternGitTutorial.git
```

### Running the Flask MVC Demo Application

**Requirements:**
- mac or linux OS
- python3 installed (3.6 or later preferred)

1. Enter the FlaskDemo directory: 
`$cd FlaskDemo`
2. Create a virtual environment in your directory. Make sure you name it 'env'. This will ensure that this program will run the same version of python no matter what machine it's on:
```
$python3 -m venv env
```
Run the command `python --version`. It should return `Python 2.7.xxx'`, showing that the virtual environment hasn't been activated yet. 
3. Activate your virtual environment: 
```
$source env/bin/activate
```
You should see the expression `(env)` in front of the shell. If you run `python --version` again, it should now return `Python 3.6.xxx'`

4. Run the command `python app.py`. It should show that a development server is up and running, and it'll give you the URL where you can see your server.


