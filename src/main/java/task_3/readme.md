## Task 3: Text To Image. Make a request to LLM to create image

Documentation: https://platform.openai.com/docs/guides/images

<details> 
<summary>Request sample to generate picture</summary>

```markdown
curl https://api.openai.com/v1/images/generations \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "model": "dall-e-3",
    "prompt": "a white siamese cat",
    "n": 1,
    "size": "1024x1024"
  }'
```
</details> 


<details> 
<summary>LLM Response sample</summary>

```json
{
  "created": 1736706103,
  "data": [
    {
      "url": "https://oaidalleapiprodscus.blob.core.windows.net/private/org-4gmoJNE5OFOy7cOREKmAVrb0/user-Q2aVPPfmKT3NE3NQbdXwJMvY/img-TPVDzM3b73pq6SwgC320rtsL.png?st=2025-01-12T17%3A21%3A43Z&se=2025-01-12T19%3A21%3A43Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=d505667d-d6c1-4a0a-bac7-5c84a87759f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2025-01-12T00%3A30%3A42Z&ske=2025-01-13T00%3A30%3A42Z&sks=b&skv=2024-08-04&sig=I33K/vdtsUIwYsrpk9wSKAbll8GjB3COXBOxzO/uZ6I%3D"
    }
  ]
}
```
</details>

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)


### Steps
1. Create request to LLM
2. Call LLM `https://api.openai.com/v1/chat/completions`. Don't forget that content type is `application/json`
3. Get response and get `url`
4. Validate with tests `task_3.TextToImageTest`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO AND CHECK IF RESPONSE PROVIDES URL AND THE FILE CAN BE CREATED FROM CONTENT**