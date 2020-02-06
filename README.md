# email-scheduler
Restful Email Scheduler with Spring and Quartz.

## Quick Start
	mvn spring-boot:run

## Features

##### SCHEDULE AN EMAIL

Method:	`POST: /v0.01/schedule`

Status: `201: created`

Content-Type: `application/json`

Body: 

	{
		"to":["example@gmail.com"],
		"from":"example@gmail.com",
		"subject":"Hi there",
		"message":"Lorem Ipsum",
		"cc":["example@gmail.com"],
		"schedule":{
			"scheduleName":"Social Campaign",
			"scheduleDate":"2020-01-13T10:59:00.000"
		}
	}

##### SCHEDULE MULTIPLE EMAILS

Method:	`POST: /v0.01/schedule/list`

Status: `202: accepted`

Content-Type: `application/json`

Body: 

	[
	  {
		  	"to":["example@gmail.com"],
			"from":"example@gmail.com",
			"subject":"Hi there",
			"message":"Lorem Ipsum",
			"cc":["example@gmail.com"],
			"schedule":{
				"scheduleName":"Social Campaign",
				"scheduleDate":"2020-01-13T10:59:00.000"
		}
	  },
	  {
		    "to":["example@gmail.com"],
			"from":"example@gmail.com",
			"subject":"Hi there",
			"message":"Lorem Ipsum",
			"cc":["example@gmail.com"],
			"schedule":{
				"scheduleName":"Student Campaign",
				"scheduleDate":"2020-01-13T10:59:00.000"
			}
	  }
		
	]

##### RETRIEVE LIST OF EMAILS IN QUEUE

Method: `GET: /v0.01/jobs`

Status: `200 Ok`


#### PAUSE ALL JOBS

Method: `PUT: /v0.01/jobs/pause/all`

Status: `202 Ok`

#### PAUSE A JOB

Method: `PUT: /v0.01/jobs/pause/{groupName}/{job_Id}`

Status: `202 Accepted`

#### RESUME ALL JOBS

Method: `PUT : /v0.01/jobs/resume/all`

Status:	`202 Accepted`

#### RESUME A JOB

Method: `PUT: /v0.01/jobs/resume/{groupName}/{job_Id}`

Status: `202 Accepted`




