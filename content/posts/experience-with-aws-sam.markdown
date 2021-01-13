---
title: "Experience With AWS SAM"
date: 2021-01-11T23:12:37-08:00
draft: true
tags:
  - aws
  - serverless
---

## Intro

In the last couple of months, I've been trying out [AWS SAM](https://aws.amazon.com/serverless/sam/). AWS SAM is pretty much a thin wrapper around AWS CloudFormation. For those of you who don't know, AWS Cloudformation is a way to programmatically control your infrastruture. Infrastructure as Code (IaC) is considered an industry best practice today, because it lets you declaratively state the underlying resources you want your applications to run on. This is useful because you write these infrastructure templates once, and generate the same immutable stack in different environments.

Back to AWS SAM. I had an idea of building an app that does the player to character assignment for the board game Avalon. Basically, players connect to a gameroom, and the host starts the game. Once the game is started, all of the players get assigned to a random character. If you're familar with real-time online system, you probably guessed that we'd need to use the WebSocket protocol to enable this two-way communication between players and the server.

Using AWS SAM, I was able to quickly spin up a few lambdas and a DynamoDB table to get up and running in a matter of hours. However, I will note that the experience was not buttery smooth. There were definitely some hiccups along the way. Below I'll discuss various topics and my experience with them.

## Documentation

Cloudformation and AWS SAM templating can be difficult to work with. Here's an [issue](https://github.com/aws/serverless-application-model/issues/1464) I submitted to the aws/sam repo. Basically, I was running into issues saying that I couldn't specify the `ProvionedThroughput` of my DynamoDB table. But the error message I was getting was `ProvisionedThroughput cannot be empty`. See how that could be confusing? Well it turns out that when you specify a GSI, you also need to specify the `ProvisionedThroughput` property there as well. I think I would've saved a solid 3 hours of Googling if AWS simply returned an error message saying that my Global Secondary Index needs to have the `ProvisionedThroughput` property set.

## Testing Locally

Writing idiomatic Go code following the SOLID principle makes it easy to test the domain layer. When it comes to testing everything end-to-end, it's a different story with AWS SAM. Unfortunately, AWS SAM does not have a way to locally test API
Gateway websocket. See this [issue](https://github.com/aws/aws-sam-cli/issues/896) for more details. This creates an extremely tedious process of pushing code to AWS to test it for real. Websockets alone can already be difficult to test since it usually involves multiple clients, so I wrote a Go program that performs the WS connections so that I can test my app functionality.

## Performance

Lambda cold starts. It's probably been discussed thousands of times. I think it really boils down to what you're building. According to the [Doherty Threshold](https://lawsofux.com/doherty-threshold.html), 400 milliseconds is maxmium time you want your users to wait before they start to lose attention. I measured my cold starts using [AWS X-Ray](https://aws.amazon.com/xray/). They were a little bit over 100ms. It's not great but it's perfectly acceptable for a game room application. If I really wanted to squeeze every drop of performance that I could get out of lambda, I could bump up the memory resources and push responses to the clients connected to the websocket in parallel. Lambda's performance with the cold starts are good enough for me here.

## Costs

I'm probably the only person that uses the app I built. I'd hate to get charged for running a server 24/7 when I'm not using it. My total costs for running this application in the past couple months have been.. $0.03. Yup, 3 total cents. And those 3 cents aren't even from running the application; it's from storing the compiled Go binaries in S3.

## Not managing infrastructure

The great thing about using serverless technology is that I'm not the one responsible for managing my infrastructure. If a node goes down, I can point the finger at AWS. I don't need to be an Ops expert or a Database admin. I can just focus on my building my product without worrying about database provisioning or load balancers and server redundancy. All of the traditional infrastracture management is just abstracted away from me, which is great.

## CI

There were Github Actions available, which made it pretty simple to set up SAM in the cloud and have it automatically build and deploy my lambdas when I push to the main repo.

## Summary

Here's an arbitrary rating of the AWS serverless technologies that I used:

1. DynamoDB: 9/10. I really like DynamoDB. It's extremely fast and a breeze to develop in locally. My unit tests create new tables in DynamoDB on every run, and deletes the tables when the tests finish. Everything is fast, and I don't have to manage infrastructure for it in production.

2. Lambda: 9/10. Minus the cold starts, the lambdas have been reliable and pleasant to work with. It's easy to test these locally since you can just feed it an event json file.

3. API Gateway Websockets: 3/10. Testing locally is near impossible. Out of the box, you can a secure websocket that you can use immediately. It seems pretty configurable if you wanted to set appropriate rate limiting to prevent abuse. The main advantage here is the ability to scale easily. Overall, this technology was definitely the weakest link and I wouldn't use it for a _real_ application.

4. SAM CLI: 8/10. Getting this to the point where I could do builds and deploys in CI was really convenient and made testing the API Gateway Websockets much faster. Overall, I felt like the CLI tool was straightforward to use and the documentation for it was sufficient.
